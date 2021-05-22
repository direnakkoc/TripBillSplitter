import { NavigationContainer } from "@react-navigation/native";
import React, { useState, useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";

export default function SummaryScreen({ route, navigation }) {
  /** Parameters which come from other pages with help of route and navigation*/
  const { username, token, tripname } = route.params;
  /** Parameters for summary page */
  const [total, setTotal] = useState(0);
  const [numberOfPurchase, setNumberOfPurchase] = useState(0);
  const [highest, setHighest] = useState(0);
  const [lowest, setLowest] = useState(0);
  const [avarage, setAvarage] = useState(0);
  /** When page opens, useEffect renders and brings summary to the page */
  useEffect(() => {
    /** Putting url in the fetch and sending it with token in the header to get summary */
    fetch("http://localhost:8080/" + tripname + "/summary/" + username, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((data) => data.json()) //After getting request, response will return as json
      .then((result) => {
        setTotal(result.total); // Setting total result of expenses
        setNumberOfPurchase(result.numberOfPurchase); // Setting number of expenses
        setHighest(result.highest); // Setting the highest price in trip
        setLowest(result.lowest); // Setting the lowest price in trip
        setAvarage(result.avarage); // Setting avarage of expenses
      });
  }, []);

  return (
    <View style={styles.body}>
      <View style={styles.headlineContainer}>
        <Text style={styles.headline}>SUMMARY</Text>
      </View>
      <View style={styles.summaryContainer}>
        <Text style={styles.texts}>Total:</Text>
        <Text style={styles.summary}>{total.toFixed(2)} €</Text>
        <Text style={styles.texts}>Number of Purchases:</Text>
        <Text style={styles.summary}>{numberOfPurchase.toFixed(0)}</Text>
        <Text style={styles.texts}>Highest Purchase:</Text>
        <Text style={styles.summary}>{highest.toFixed(2)} €</Text>
        <Text style={styles.texts}>Lowest Purchase:</Text>
        <Text style={styles.summary}>{lowest.toFixed(2)} €</Text>
        <Text style={styles.texts}>Avarage of Purchases:</Text>
        <Text style={styles.summary}>{avarage.toFixed(2)} €</Text>
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
    paddingTop: 20,
    alignItems: "center",
    justifyContent: "center",
  },
  headline: {
    fontSize: 50,
    color: "#264653",
    backgroundColor: "transparent",
  },
  summaryContainer: {
    alignItems: "stretch",
    justifyContent: "space-around",
    height: 400,
    width: 250,
    marginTop: 30,
  },
  texts: {
    fontSize: 20,
    borderBottomWidth: 0.5,
    borderBottomColor: "black",
  },
  summary: {
    fontSize: 30,
  },
});
