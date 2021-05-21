import React, { useState, useEffect } from "react";
import {
  RefreshControl,
  ScrollView,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";

const wait = (timeout) => {
  return new Promise(resolve => setTimeout(resolve, timeout));
}

export default function HomeScreen({ route, navigation }) {
   /** Parameters which come from other pages with help of route and navigation*/
  const { username, token } = route.params;
  /** Parameters */
  const [totalAmount, setTotalAmount] = useState(0);
  const [tripname, setTripname] = useState("");
  const [refreshing, setRefreshing] = React.useState(false);

  /** When page opens, useEffect renders and brings total amount */
  useEffect(() => {
    getTotalAmount()
  }, []);
  /** function for going to Expenses page with username and token parameters */
  function getUserExpenses() {
    navigation.push("Expenses", {
      username: username,
      token: token,
    });
  }
  /** function for going to Trip adding page with username and token parameters*/
  function addTrip() {
    navigation.push("Add Trip", {
      username: username,
      token: token,
    });
  }
/** function for going to Expense adding page with username and token parameters*/
  function addExpense() {
    navigation.push("Add Expense", {
      username: username,
      token: token,
    });
  }
  /** function for going to Trip expenses page with username ,tripname and token parameters*/
  /** If trip name is defined, function throws a sensible error message */
  function showTripExpenses() {
    if (tripname) {
      fetch("http://192.168.1.3:8080/"+tripname+"/check",{
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        }}).then((response)=> {
          if(response.ok){
            navigation.push("Trip Expenses", {
        username: username,
        token: token,
        tripname: tripname,
      });
          }else{
            alert("Trip is not existed");
          }
        })
      
    } else {
      alert("Please add the trip name.");
    }
  }
  /** Logout function */
  /** After log out, sends to Login page and clean the parameters */
  function logout() {
    navigation.push("Login", {
      username: "",
      token: "",
    });
  }
  /** function for getting user's total of expenses */
  /** Putting inside all the parameters which is needed to get total of expenses */
  function getTotalAmount(){
    fetch("http://192.168.1.3:8080/" + username + "/summary/user", {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    }).then((response) => response.json()) //After getting total amount, response will return as json
    .then((data) => {
      setTotalAmount(data);
    });
  }
  
  /** function for refreshing the page to get recent information */
  const onRefresh = React.useCallback(() => {
    getTotalAmount();
    setRefreshing(true);
    wait(2000).then(() => setRefreshing(false));
  }, []);

  return (
    <ScrollView
    contentContainerStyle={styles.scrollView}
        refreshControl={
          <RefreshControl
            refreshing={refreshing}
            onRefresh={onRefresh}
          />
        }>
      <View style={styles.profileContainer}>
        <Text style={{ fontSize: 25 }}>USERNAME</Text>
        <Text style={{ fontSize: 25, color: "#2A9D8F" }}>{username}</Text>
        <Text style={{ fontSize: 25 }}>TOTAL AMOUNT</Text>
        <Text style={{ fontSize: 20, color: "red" }}>
          {totalAmount.toFixed(2)} €
        </Text>
      </View>
      <View style={styles.inputContainer}>
        <TouchableOpacity style={styles.button} onPress={getUserExpenses}>
          <Text>ALL MY EXPENSES</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={addTrip}>
          <Text>TRIP</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={addExpense}>
          <Text>EXPENSE</Text>
        </TouchableOpacity>
        <TextInput
          placeholder="Trip Name"
          style={styles.inputStyle}
          value={tripname}
          onChangeText={value=>setTripname(value.trim())}
        />
        <TouchableOpacity style={styles.button} onPress={showTripExpenses}>
          <Text>SHOW EXPENSES</Text>
        </TouchableOpacity>
      </View>
      <View style={styles.logoutContainer}>
        <TouchableOpacity style={styles.logoutButton} onPress={logout}>
          <Text>LOGOUT</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}
const styles = StyleSheet.create({
  scrollView: {
    flex: 1,
  },
  profileContainer: {
    height: 200,
    width: "100%",
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#E9C46A",
  },
  inputContainer: {
    alignItems: "center",
    justifyContent: "center",
    height: 300,
    width: "100%",
  },
  logoutContainer: {
    width: "100%",
    marginTop: 60,
    justifyContent: "center",
    alignItems: "center",
  },
  inputStyle: {
    height: 40,
    width: 200,
    borderBottomWidth: 1,
    borderBottomColor: "#E76F51",
    textAlign: "center",
    fontSize: 30,
    marginTop: 10,
  },
  button: {
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#2A9D8F",
    height: 40,
    width: 200,
    marginTop: 10,
    borderRadius:8
  },
  logoutButton: {
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#E76F51",
    height: 40,
    width: 200,
    marginTop: 10,
    borderRadius:8
  },
});
