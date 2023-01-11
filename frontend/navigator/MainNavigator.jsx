import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import HomeScreen from '../Screen/HomeScreen'
import BookDetailScreen from '../Screen/BookDetailScreen'

const stack = createNativeStackNavigator()
const MainNavigator = () => {
  return (
   <stack.Navigator>
    <stack.Screen options={{headerShown: false}} name="Home" component={HomeScreen}></stack.Screen>
    <stack.Screen options={{headerShown: false}} name="BookDetail" component={BookDetailScreen}></stack.Screen>
   </stack.Navigator>
  )
}

export default MainNavigator

const styles = StyleSheet.create({})