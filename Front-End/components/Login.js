import React, { useState } from "react";
import {
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from "react-native";

export default function Login({ navigation }) {
  /** Parameters to login the system */
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  /** function for login the system with inputs */
  /** If inputs are empty, function throws a sensible error message */
  function signIn() {
    if (username && password) {
      /** Putting url and parameters to the fetch to login */
      fetch(
        "http://192.168.1.3:8080/login?username=" +
          username +
          "&password=" +
          password
      )
        .then((response) => response.text()) //After login, token will be text
        .then((text) => {
          if (text == "Error") {
            alert("Please try again.");
          } else {
            clearPassword();
            navigation.push("Home", {
              username: username,
              token: text,
            });
          }
        })
        .catch((e) => console.log(e));
    } else {
      alert("All fields are mandatory");
    }
  }
  /** function for making application safer */
  function clearPassword() {
    alert("Successfully Logged in");
    setPassword("");
  }
  /** function for sending user to signup page to register*/
  function signup() {
    navigation.navigate("Signup");
  }

  return (
    <View style={styles.container}>
      <View style={{ height:200, alignItems: "center", justifyContent: "center" }}>
        <Text style={styles.headline}>Login</Text>
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
        <View style={{ marginTop:20, justifyContent: "center", alignItems: "center" }}>
        <TouchableOpacity style={styles.button} onPress={signIn}>
          <Text>LOGIN</Text>
        </TouchableOpacity>
      </View>
      </View>
      
      <View style={{ flex: 1 }}>
        <Text>
          Don't have an account?{" "}
          <TouchableOpacity onPress={signup}>
            <Text style={{ color: "blue" }}>Signup</Text>
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
    justifyContent: "space-evenly",
    height: 250,
    width: 250,
  },
  headline: {
    fontSize: 80,
    color: "#2A9D8F",
  },
  texts: {
    fontSize: 10,
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
