import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";

export default function TripAddScreen({ route, navigation }) {
  /** Parameters which come from other pages with help of route and navigation*/
  const { username, token } = route.params;
  /** Parameter */
  const [tripName, setTripName] = useState("");

  /** Adding trip method */
  /** Creating data and putting inside all the parameters which is needed to add a trip */
  function addTrip() {
    const data = { tripname: tripName, tripStatus: true };
    /** If tripname is empty , function does not work. */
    /** Putting url in the fetch and sending it with body and with token in the header */
    if (tripName) {
      fetch("http://localhost:8080/trip/add", {
        method: "POST", //This is a Post request
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: JSON.stringify(data),
      })
        .then((response) => response.text()) //After adding the trip, response will return as text
        .then((result) => alert(result))
        .catch((error) => {
          console.log("Error:", error);
        });
    } else {
      alert("All fields are mandatory");
    }
  }
  /** Adding the user to the trip */
  function joinTrip() {
    /** If tripname is empty , function does not work. */
    /** Putting url in the fetch and sending it with body and with token in the header */
    if (tripName) {
      fetch("http://localhost:8080/" + tripName + "/join/" + username, {
        method: "POST", //This is a Post request
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
      })
        .then((response) => response.text()) //After adding user to the trip, response will return as text
        .then((result) => {
          /** If there is any error, throw alert and show error */
          if (result.substring(0, 5) == "Error") {
            alert(result);
            /** If there is no error, show message and go to home page */
          } else {
            alert(result);
            navigation.push("Home", {
              username: username,
              token: token,
            });
          }
        })
        .catch((error) => {
          console.log("Error:", error);
        });
    } else {
      alert("All fields are mandatory");
    }
  }

  return (
    <View style={styles.body}>
      <View style={styles.headlineContainer}>
        <Text style={styles.headline}>TRIP</Text>
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.texts}>Trip</Text>
        <TextInput
          style={styles.textInputs}
          placeholder="Trip"
          value={tripName}
          onChangeText={(value) => setTripName(value.trim())}
        />
        <TouchableOpacity style={styles.button} onPress={addTrip}>
          <Text>ADD</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button} onPress={joinTrip}>
          <Text>JOIN</Text>
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
    width: 250,
  },
  texts: {
    fontSize: 10,
  },
  textInputs: {
    fontSize: 25,
    width: 250,
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
  },
});
