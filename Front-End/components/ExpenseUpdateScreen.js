import NumericInput from "@wwdrew/react-native-numeric-textinput";
import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View
} from "react-native";

export default function ExpenseUpdateScreen({ route, navigation }) {
   /** Parameters which come from other pages with help of route and navigation*/
  const {
    username,
    token,
    expenseId,
    expenseDescription,
    expenseAmount,
    tripname,
  } = route.params;
  /** Parameters for the page to update expense */
  const [expenseDescriptionToUpdate, setExpenseDescriptionToUpdate] =
    useState(expenseDescription);
  const [expenseAmountToUpdate, setExpenseAmountToUpdate] =
    useState(expenseAmount);
  const [tripnameToUpdate, setTripnameToUpdate] = useState(tripname);

  /** function for updating the expense*/
  /** Creating data and putting inside all the parameters which is needed to update an expense */
  /** If there is any error, throws the screen */
  function updateExpense() {
    const data = {
      expenseId: expenseId,
      expenseDescription: expenseDescriptionToUpdate,
      expenseAmount: expenseAmountToUpdate,
      trip: { tripname: tripnameToUpdate },
      user: { username: username },
    };
    /** Putting url in the fetch and sending it with body to update with token in the header */
    fetch("http://192.168.1.3:8080/expense/update", {
      method: "POST", //This is a Post request
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.text()) //After updating, response will return as text
      .then((data) => {
        /** If there is any error, throw alert and show error */
        if (data.substring(0, 5) == "Error") {
          alert(data);
          /** If there is no error, show message and go to Expenses page */
        } else {
          alert(data);
          navigation.push("Home", {
            username: username,
            token: token,
          });
        }
      })
      .catch((error) => {
        alert("Error: ", error);
      });
  }

  return (
    <View style={styles.body}>
      <View style={styles.headlineContainer}>
        <Text style={styles.headline}>UPDATE EXPENSE</Text>
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.texts}>Description</Text>
        <TextInput
          style={styles.textInputs}
          placeholder="Description"
          onChangeText={setExpenseDescriptionToUpdate}
        />
        <Text style={styles.texts}>Amount</Text>
        <NumericInput
          style={styles.textInputs}
          placeholder="Amount"
          type="decimal"
          decimalPlaces={2} // After the comma, getting last 2 numbers
          value={expenseAmountToUpdate}
          onUpdate={(amount) => setExpenseAmountToUpdate(amount)}
        />
        <Text style={styles.texts}>Trip Name</Text>
        <TextInput
          style={styles.textInputs}
          placeholder="Trip Name"
          onChangeText={setTripnameToUpdate}
        />
      </View>
      <View style={{ width: "100%" }}>
        <TouchableOpacity style={styles.button} onPress={updateExpense}>
          <Text>UPDATE</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  body: {
    flex: 1,
    height: "100%",
    alignItems: "center",
  },
  headlineContainer: {
    paddingTop: 40,
    backgroundColor: "transparent",
    alignItems: "center",
    justifyContent: "center",
  },
  headline: {
    width: 300,
    textAlign: "center",
    fontSize: 40,
    color: "#264653",
  },
  inputContainer: {
    alignItems: "center",
    justifyContent: "center",
    height: 200,
    width: "100%",
    marginTop: 30,
  },
  texts: {
    fontSize: 10,
  },
  textInputs: {
    width: 300,
    fontSize: 25,
    textAlign: "center",
    justifyContent: "center",
    alignItems: "center",
    borderBottomColor: "#2A9D8F",
    borderBottomWidth: 1,
  },
  button: {
    backgroundColor: "#2A9D8F",
    height: 40,
    width: "100%",
    alignItems: "center",
    justifyContent: "center",
    marginTop: 30,
  },
});
