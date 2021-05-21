import React, { useEffect, useState } from "react";
import { FlatList, ScrollView, StyleSheet, Text, View } from "react-native";

export default function ResultScreen({ route,navigation }) {
  /** Parameters which come from other pages with help of route */
  const { username, token, tripname } = route.params;
  /** Parameter */
  const [result, setResult] = useState([]);

  /** When page opens, useEffect renders and brings result to the page */
  useEffect(() => {
    /** Putting url in the fetch and sending it with token in the header to get result */
    fetch("http://192.168.1.3:8080/" + tripname + "/result/" + username, {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => {
        if(response.ok){
          return response.json();
        }else{
          alert("There is no expense");
          navigation.navigate("Home");
        }
      }) //After getting request, response will return as json
      .then((data) => {
        setResult(data);
      })
      .catch((e) => Alert.alert(e));
  }, []);

  return (
    <View style={styles.body}>
      <View style={styles.headline}>
        <Text style={{ fontSize: 40 }}>RESULT</Text>
      </View>

      <View style={styles.listWrapper}>
        <Text style={styles.headRow}>Username</Text>
        <Text style={styles.headRow}>Spent</Text>
        <Text style={styles.headRow}>Result</Text>
      </View>
      <ScrollView style={styles.scrollView}>
        <FlatList
          data={result}
          renderItem={({ item }) => (
            <View style={styles.listWrapper}>
              <Text style={styles.row}>{item.username}</Text>
              <Text style={styles.row}>{item.spent.toFixed(2)} €</Text>
              <Text style={styles.row}>{item.result.toFixed(2)} €</Text>
            </View>
          )}
          keyExtractor={(item, index) => item + index.toString()}
        />
      </ScrollView>
    </View>
  );
}
const styles = StyleSheet.create({
  body: {
    width: "100%",
    height: "100%",
    justifyContent: "center",
  },
  listWrapper: {
    flexDirection: "row",
    flexWrap: "wrap",
    backgroundColor: "#6B705C",
    borderBottomWidth: 0.5,
    borderBottomColor: "black",
  },
  scrollView: {
    height: 300,
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
    height: 100,
    backgroundColor: "transparent",
  },
  button: {
    backgroundColor: "#2A9D8F",
    height: 40,
    width: "100%",
    alignItems: "center",
    justifyContent: "center",
  },
});
