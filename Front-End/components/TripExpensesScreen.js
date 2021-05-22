import React, { useEffect, useState } from "react";
import {
  RefreshControl,
  ScrollView,
  Alert,
  FlatList,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from "react-native";

const wait = (timeout) => {
  return new Promise((resolve) => setTimeout(resolve, timeout));
};

export default function TripExpensesScreen({ route, navigation }) {
  /** Parameters which come from other pages with help of route and navigation*/
  const { username, token, tripname } = route.params;
  /** Parameters */
  const [tripExpenses, setTripExpenses] = useState([]);
  const [refreshing, setRefreshing] = React.useState(false);
  /** When page opens, useEffect renders and brings all of the expenses in trip */
  useEffect(() => {
    getAllExpenses();
  }, []);
  /** function for going to Result page with username,trip name and token parameters */
  function getResult() {
    navigation.navigate("Result", {
      username: username,
      token: token,
      tripname: tripname,
    });
  }
  /** function for going to Summary page with username,tripname and token parameters */
  function getSummary() {
    navigation.navigate("Summary", {
      username: username,
      token: token,
      tripname: tripname,
    });
  }
  /** function for closing the trip */
  /** If tripname is empty, function does not work. */
  function closeTrip() {
    if (tripname) {
      fetch("http://localhost:8080/" + tripname + "/close", {
        method: "POST", //This is a Post request
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then(navigation.navigate("Home")) // After closing the trip, navigate to Home page
        .catch((error) => {
          console.error("Error:", error);
        });
    } else {
      alert("There is no Trip");
    }
  }
  /** function to get all the expenses in the trip */
  function getAllExpenses() {
    fetch("http://localhost:8080/expenses/" + tripname + "/" + username, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((data) =>{
        if(data.ok){
          return data.json();
        }else{
          alert("There is no expense");
          navigation.navigate("Home");
        }
      }) //After getting all of the trip expenses, response will return as json
      .then((result) => {
        setTripExpenses(result);
      })
      .catch((e) => Alert.alert(e));
  }
  /** function for creating header of flat list */
  function getHeader() {
    return (
      <View style={styles.listWrapper}>
        <Text style={styles.headRow}>Username</Text>
        <Text style={styles.headRow}>Description</Text>
        <Text style={styles.headRow}>Amount</Text>
        <Text style={styles.headRow}>Trip</Text>
      </View>
    );
  }
  /** function for refreshing the page */
  const onRefresh = React.useCallback(() => {
    getAllExpenses();
    setRefreshing(true);
    wait(2000).then(() => setRefreshing(false));
  }, []);

  return (
    <ScrollView
      contentContainerStyle={styles.scrollView}
      refreshControl={
        <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
      }
    >
      <View style={styles.headline}>
        <Text style={{ fontSize: 30 }}>TRIP EXPENSES</Text>
      </View>
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={styles.button} onPress={getResult}>
          <Text>RESULT</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={getSummary}>
          <Text>SUMMARY</Text>
        </TouchableOpacity>
      </View>

      <ScrollView style={styles.list}>
        <FlatList
          data={tripExpenses}
          renderItem={({ item }) => (
            <View style={styles.listWrapper} key={item.user.username}>
              <Text style={styles.row}>{item.user.username}</Text>
              <Text style={styles.row}>{item.expenseDescription}</Text>
              <Text style={styles.row}>{item.expenseAmount.toFixed(2)} €</Text>
              <Text style={styles.row}>{item.trip.tripname}</Text>
            </View>
          )}
          ListHeaderComponent={getHeader}
        />
      </ScrollView>
      <View style={{ width: "100%", alignItems: "center" }}>
        <TouchableOpacity style={styles.button} onPress={closeTrip}>
          <Text>CLOSE TRIP</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}
const styles = StyleSheet.create({
  scrollView: {
    flex: 1,
  },
  listWrapper: {
    flexDirection: "row",
    flexWrap: "wrap",
    backgroundColor: "#6B705C",
    borderBottomWidth: 0.5,
    borderBottomColor: "black",
  },
  list: {
    height: 350,
    width: "100%",
  },
  headRow: {
    flex: 1,
    textAlign: "center",
    backgroundColor: "#CB997E",
  },
  row: {
    flex: 1,
    height: 40,
    textAlign: "center",
    justifyContent: "center",
    alignItems: "center",
    textAlignVertical: "center",
    lineHeight: 40,
    backgroundColor: "#FFE8D6",
  },
  headline: {
    alignItems: "center",
    justifyContent: "center",
    height: 80,
    backgroundColor: "transparent",
  },
  buttonContainer: {
    height: 60,
    width: "100%",
    flexDirection: "row",
    justifyContent: "space-around",
  },
  button: {
    height: 40,
    width: 120,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#2a9d8f",
    borderRadius: 8,
  },
});
