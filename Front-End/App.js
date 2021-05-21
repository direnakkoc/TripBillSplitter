import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import React from "react";
import { StyleSheet } from "react-native";
import ExpenseAddScreen from "./components/ExpenseAddScreen.js";
import ExpenseUpdateScreen from "./components/ExpenseUpdateScreen.js";
import HomeScreen from "./components/HomeScreen.js";
import Login from "./components/Login.js";
import ResultScreen from "./components/ResultScreen.js";
import Signup from "./components/Signup.js";
import SummaryScreen from "./components/SummaryScreen.js";
import TripAddScreen from "./components/TripAddScreen.js";
import TripExpensesScreen from "./components/TripExpensesScreen.js";
import UsersExpensesScreen from "./components/UsersExpensesScreen.js";


const Stack = createStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Login" component={Login} options={{ headerShown: false }} />
        <Stack.Screen name="Signup" component={Signup} options={{ headerShown: false }} />
        <Stack.Screen name="Home" component={HomeScreen} options={{ headerShown: false }} />
        <Stack.Screen name="Expenses" component={UsersExpensesScreen} />
        <Stack.Screen name="Add Trip" component={TripAddScreen} />
        <Stack.Screen name="Add Expense" component={ExpenseAddScreen} />
        <Stack.Screen name="Update Expense" component={ExpenseUpdateScreen} />
        <Stack.Screen name="Trip Expenses" component={TripExpensesScreen} />
        <Stack.Screen name="Summary" component={SummaryScreen} />
        <Stack.Screen name="Result" component={ResultScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
});
