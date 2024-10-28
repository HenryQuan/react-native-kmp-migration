import {useNavigation} from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import {
  Button,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TextInput,
  useColorScheme,
  View,
} from 'react-native';
import {Colors} from 'react-native/Libraries/NewAppScreen';
// type hint doesn't seem to work somehow
import { FilterUseCase, ShipAdditional, ShipAdditionalServiceJS } from "kmp-migration";

function HomeScreen(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };
  const navigator = useNavigation();
  const service = new ShipAdditionalServiceJS();
  const [shipAdditional, setShipAdditional] = useState(new Map<string, ShipAdditional>());
  const [filterUseCase, setFilterUseCase] = useState(null);

  // 
  useEffect(() => {
    service.getShipAdditionalPromise().then((result) => {
      console.log('getShipAdditionalPromise is finished');
      // see https://kotlinlang.org/docs/js-to-kotlin-interop.html#kotlin-types-in-javascript
      setShipAdditional(result.asJsReadonlyMapView());
      setFilterUseCase(new FilterUseCase(result));
    }).catch((error) => {
      console.error('getShipAdditionalPromise', error);
    });
  }, []);
  //

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <View style={styles.centerView}>
        <Text style={styles.centerText}>
          Just consider this as an existing screen
        </Text>
        <View style={styles.navigateView}>
          <Button
            title="Go to complex screen"
            onPress={() => {
              navigator.navigate('Complex');
            }}
          />
        </View>
      </View>
      <TextInput placeholder='Filter by alphaPiercingHE' onChangeText={(text) => {
        const result = filterUseCase?.filterHEPen(Number(text));
        console.log('filterHEPen', result);
      }} />;
      <ScrollView style={{height: "100%"}}>
        {shipAdditionalList(shipAdditional)}
      </ScrollView>
    </SafeAreaView>
  );
}

function shipAdditionalList(shipAdditional: Map<string, ShipAdditional>): React.JSX.Element {
  const li = [];
  shipAdditional.forEach((key, value) => {
    const keyString = key.toString();
    li.push(
      <View key={keyString} style={{padding: 8}}>
        <Text>{keyString}</Text>
        <Text>{value.toString()}</Text>
      </View>
    );
  });

  return li;
}

const styles = StyleSheet.create({
  centerView: {
    alignItems: 'center',
  },
  centerText: {
    textAlign: 'center',
  },
  navigateView: {
    padding: 10,
  },
});

export default HomeScreen;
