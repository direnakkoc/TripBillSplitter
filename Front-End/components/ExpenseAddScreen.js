import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";
import NumericInput from "@wwdrew/react-native-numeric-textinput";

export default function ExpenseAddScreen({ route, navigation }) {
  /** Parameters which come from other pages with help of route and navigation */
  const { username, token } = route.params;
  /** Parameters */
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState();
  const [tripname, setTripname] = useState("");

  /** Adding expense method */
  /** Creating data and putting inside all the parameters which is needed to add an expense */
  function addExpense() {
    const data = {
      expenseDescription: description,
      expenseAmount: amount,
      trip: { tripname: tripname },
      user: { username: username },
    };
    /** If there is any empty parameter, function does not work. */
    /** Putting url in the fetch and sending it with body and with token in the header */
    if (tripname && description && amount) {
      fetch("http://localhost:8080/expense/add", {
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
          } 
          /** If there is no error, show message and go to home page */
          else {
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
    } else {
      alert("All fields are mandatory");
    }
  }

  return (
    <View style={styles.body}>
      <View style={styles.headlineContainer}>
        <Text style={styles.headline}>ADD EXPENSE</Text>
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.texts}>Description</Text>
        <TextInput
          style={styles.textInputs}
          placeholder="Description"
          onChangeText={setDescription}
        />
        <Text style={styles.texts}>Amount</Text>
        <NumericInput
          style={styles.textInputs}
          placeholder="Amount"
          type="decimal"
          decimalPlaces={2} // After the comma, getting last 2 numbers
          value={amount}
          onUpdate={(amount) => setAmount(amount)}
        />
        <Text style={styles.texts}>Trip Name</Text>
        <TextInput
          style={styles.textInputs}
          placeholder="Trip Name"
          onChangeText={(value) => setTripname(value.trim())}
        />
        <TouchableOpacity style={styles.button} onPress={addExpense}>
          <Text>ADD</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  body: {
    height: "100%",
    alignItems: "center",
  },
  headlineContainer: {
    paddingTop: 40,
    paddingBottom: 80,
    backgroundColor: "transparent",
    alignItems: "center",
    justifyContent: "center",
  },
  headline: {
    fontSize: 50,
    color: "#264653",
  },
  inputContainer: {
    alignItems: "center",
    justifyContent: "space-around",
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
