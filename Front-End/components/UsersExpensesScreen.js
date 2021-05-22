import React, { useEffect, useState } from "react";
import {
  RefreshControl,
  ScrollView,
  Alert,
  FlatList,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  LogBox
} from "react-native";
import Icon from "react-native-vector-icons/FontAwesome";

const wait = (timeout) => {
  return new Promise((resolve) => setTimeout(resolve, timeout));
};

export default function UsersExpensesScreen({ route, navigation }) {
  /** Parameters which come from other pages with help of route and navigation*/
  const { username, token } = route.params;
  /** Parameters */
  const [expenses, setExpenses] = useState([]);
  const [refreshing, setRefreshing] = React.useState(false);
  /** When page opens, useEffect renders and brings all of the user's expenses */
  useEffect(() => {
    getExpenses();
  }, []);
  /** function for sending user to Expense adding page with token and username */
  function addExpense() {
    navigation.push("Add Expense", {
      username: username,
      token: token,
    });
  }
  /** function for creating header of flat list */
  function getHeader() {
    return (
      <View style={styles.listWrapper}>
        <Text style={styles.headRow}>Description</Text>
        <Text style={styles.headRow}>Amount</Text>
        <Text style={styles.headRow}>Trip</Text>
        <Text style={styles.headRow}>Del/Upd</Text>
      </View>
    );
  }
  /** function to get all the expenses for user */
  function getExpenses() {
    fetch("http://localhost:8080/expenses/" + username + "/user", {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json()) //After getting all of the user expenses, response will return as json
      .then((result) => {
        setExpenses(result);
      })
      .catch((e) => Alert.alert(e));
  }
  /** function for deleting expense */
  /** If user click the delete which is in the list, function sends expense id and tripname to delete the expense */
  function deleteExpense(id, tripname) {
    fetch("http://localhost:8080/expense/delete/" + id + "/" + tripname, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.text()) //After deleting expense, response will return as text
      .then((text) => alert(text))
      .then(() =>
        navigation.push("Home", {
          username: username,
          token: token,
        })
      );
  }
  /** function for sending user to updating page */
  /** When function works, user goes with expense informations which will be updated */
  function updateExpense(params) {
    navigation.push("Update Expense", {
      username: username,
      token: token,
      expenseId: params.expenseId,
      expenseDescription: params.expenseDescription,
      expenseAmount: params.expenseAmount,
      tripname: params.trip.tripname,
    });
  }
  /** function for refreshing the page */
  const onRefresh = React.useCallback(() => {
    getExpenses();
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
        <Text style={{ fontSize: 30 }}>ALL MY EXPENSES</Text>
      </View>
        <FlatList
          data={expenses}
          renderItem={({ item }) => (
            <View style={styles.listWrapper}>
              <Text style={styles.row}>{item.expenseDescription}</Text>
              <Text style={styles.row}>{item.expenseAmount.toFixed(2)}-€</Text>
              <Text style={styles.row}>{item.trip.tripname}</Text>
              <Text style={styles.row}>
                <TouchableOpacity
                  style={{
                    width: 30,
                    height: 30,
                    backgroundColor: "green",
                    alignItems: "center",
                    justifyContent: "center",
                    borderRadius: 3,
                  }}
                  onPress={() =>
                    deleteExpense(item.expenseId, item.trip.tripname)
                  }
                >
                  <Icon
                    name="trash"
                    backgroundColor="white"
                    style={{ fontSize: 20 }}
                  ></Icon>
                </TouchableOpacity>
                <TouchableOpacity
                  style={{
                    width: 30,
                    height: 30,
                    backgroundColor: "yellow",
                    alignItems: "center",
                    justifyContent: "center",
                    borderRadius: 4,
                    marginLeft: 5,
                  }}
                  onPress={() => updateExpense(item)}
                >
                  <Icon
                    name="wrench"
                    color="white"
                    style={{ fontSize: 20 }}
                  ></Icon>
                </TouchableOpacity>
              </Text>
            </View>
          )}
          keyExtractor={(item, index) => item + index.toString()}
          ListHeaderComponent={getHeader}
        />
      <View style={{ width: "100%", alignItems: "center" }}>
        <TouchableOpacity style={styles.button} onPress={addExpense}>
          <Text>ADD</Text>
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
    lineHeight: 40,
    backgroundColor: "#FFE8D6",
  },
  headline: {
    alignItems: "center",
    justifyContent: "center",
    height: 80,
    backgroundColor: "transparent",
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
