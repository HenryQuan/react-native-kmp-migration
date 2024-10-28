/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './src/App';
import {name as appName} from './app.json';

import * as React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import HomeScreen from './src/Home';

// Polyfill for ReadableStream and TextDecoder
// This will make ktor run on React Native projects
import { ReadableStream as ReadableStreamPolyfill } from 'web-streams-polyfill';
import TextEncoder from 'react-native-fast-encoder';

if (typeof window !== 'undefined') {
  window.ReadableStream = ReadableStreamPolyfill;
  window.TextDecoder = TextEncoder;
  console.info('Polyfill for ReadableStream and TextDecoder applied');
  console.log('window.ReadableStream: ', window.ReadableStream);
  console.log('window.TextDecoder: ', window.TextDecoder);
} else {
  // we are in big problems here
}
//
//

const Stack = createNativeStackNavigator();

function Main() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Complex" component={App} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

AppRegistry.registerComponent(appName, () => Main);
