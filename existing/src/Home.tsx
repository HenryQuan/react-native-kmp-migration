import {useNavigation} from '@react-navigation/native';
import React, {useEffect, useState} from 'react';
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
import ShipAdditionalList, {useShipAdditional} from './ShipAdditional';

function HomeScreen(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };
  const navigator = useNavigation();
  const {shipAdditional, filterUseCase} = useShipAdditional();
  const [filteredList, setFilteredList] = useState<readonly string[]>([]);

  const hasFilterResult = () => filteredList.length > 0;

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
              //@ts-ignore, how to fix?
              navigator.navigate('Complex');
            }}
          />
        </View>
      </View>
      <TextInput
        placeholder="Filter by alphaPiercingHE"
        onChangeText={text => {
          const result = filterUseCase?.filterHEPen(Number(text));
          const resultList = result?.asJsReadonlyArrayView();
          setFilteredList(resultList ?? []);
        }}
      />
      <Text>
        {hasFilterResult()
          ? 'Filtered result:'
          : 'No filter result, showing all ships'}
      </Text>
      <ScrollView style={{height: '100%'}}>
        {hasFilterResult() ? (
          filteredList.map((key, index) => {
            return (
              <View key={key + index} style={{padding: 2}}>
                <Text>{key}</Text>
              </View>
            );
          })
        ) : (
          <ShipAdditionalList shipAdditional={shipAdditional} />
        )}
      </ScrollView>
    </SafeAreaView>
  );
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
