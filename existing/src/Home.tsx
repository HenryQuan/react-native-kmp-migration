import {useNavigation} from '@react-navigation/native';
import React from 'react';
import {
  Button,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';
import {Colors} from 'react-native/Libraries/NewAppScreen';

function HomeScreen(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };
  const navigator = useNavigation();

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
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  centerView: {
    height: '100%',
    justifyContent: 'center',
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
