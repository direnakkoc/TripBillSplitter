import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";

export default function Signup({ navigation }) {
  /** Parameters to register*/
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [repeatedPassword, setRepeatedPassword] = useState("");

  /** function for registering to the system with parameters */
  /** If inputs are empty, function throws a sensible error message */
  function signup() {
    if (username && password && repeatedPassword) {
      /** If passwords does not match, function throws a sensible error message */
      if (password == repeatedPassword) {
        /** Putting url and parameters to the fetch to register */
        fetch(
          "http://192.168.1.3:8080/users/add?username=" +
            username +
            "&password=" +
            password,
          {
            method: "POST", //This is a Post request
          }
        )
          .then((response) => response.text()) //After registering, response will return as text
          .then((text) => {
            /** If there is any error, throw alert and show error */
            if (text.substring(0, 5) == "Error") {
              alert(text);
              /** If there is no error, show message and go to Login page */
            } else {
              alert(text);
              setUsername("");
              setRepeatedPassword("");
              setPassword("");
              navigation.navigate("Login");
            }
          })
          .catch((error) => {
            alert(error.message);
          });
      } else {
        alert("Passwords are not matched. Please try again.");
      }
    } else {
      alert("All fields are mandatory");
    }
  }
  /** function brings to user to Login page */
  function goToLogin() {
    navigation.navigate("Login");
  }

  return (
    <View style={styles.container}>
      <View style={{ height:200, alignItems: "center", justifyContent: "center" }}>
        <Text style={styles.headline}>Sign up</Text>
      </View>
      <View style={styles.inputContainer}>
        <Text style={styles.texts}>Username:</Text>
        <TextInput
          style={styles.textInputs}
          placeholder="Username"
          value={username}
          onChangeText={(value) => setUsername(value.trim())}
        />
        <Text style={styles.texts}>Password:</Text>
        <TextInput
          style={styles.textInputs}
          secureTextEntry
          placeholder="Password"
          value={password}
          onChangeText={(value) => setPassword(value.trim())}
        />
        <Text style={styles.texts}>Repeat Password:</Text>
        <TextInput
          style={styles.textInputs}
          secureTextEntry
          placeholder="Repeat Your Password"
          value={repeatedPassword}
          onChangeText={(value) => setRepeatedPassword(value.trim())}
        />
        <View style={{marginTop:20, justifyContent:"center",alignItems:"center"}}>
          <TouchableOpacity style={styles.button} onPress={signup}>
          <Text>SIGNUP</Text>
        </TouchableOpacity>
        </View>
        
      </View>
      <View style={{ flex: 1 }}>
        <Text>
          I have an account?{" "}
          <TouchableOpacity onPress={goToLogin}>
            <Text style={{ color: "blue" }}>Login</Text>
          </TouchableOpacity>
        </Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  inputContainer: {
    alignItems: "stretch",
    justifyContent: "space-around",
    height: 250,
    width: 250,
  },
  headline: {
    fontSize: 80,
    color: "#2A9D8F",
  },
  texts: {
    fontSize: 10,
    paddingTop: 10,
  },
  textInputs: {
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
    width: 200,
    alignItems: "center",
    justifyContent: "center",
    borderRadius: 50,
  },
});
