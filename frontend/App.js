import 'react-native-gesture-handler'
import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import HomeScreen from './Screen/HomeScreen';
import {TailwindProvider} from 'tailwind-rn';
import utilities from './tailwind.json';
import { NavigationContainer } from '@react-navigation/native';
import { Provider } from 'react-redux';
import Store from './Store';
import RootNavigator from './navigator/RootNavigator';


export default function App() {
  return (
    
    <TailwindProvider utilities={utilities}>
      <Provider store={Store}>
      <NavigationContainer>
       <RootNavigator/>
      </NavigationContainer>     
      </Provider>
    
    
    </TailwindProvider>
   
  //  <Text>Hello</Text>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
