import { StyleSheet, Text, View } from 'react-native'
import React, { useCallback, useEffect } from 'react'
import { createDrawerNavigator } from '@react-navigation/drawer'
import HomeScreen from '../Screen/HomeScreen'
import LoginScreen from '../Screen/LoginScreen'
import { createNativeStackNavigator } from '@react-navigation/native-stack'

import { createAppContainer } from 'react-navigation'
import { useDispatch, useSelector } from 'react-redux'
import AddBookScreen from '../Screen/AddBookScreen'
import CheckoutScreen from '../Screen/CheckoutScreen'
import { UserLogin } from '../Actions/UserAction'
import RegisterScreen from '../Screen/RegisterScreen'
import BookDetailScreen from '../Screen/BookDetailScreen'
import MainNavigator from './MainNavigator'
import HistoryScreen from '../Screen/HistoryScreen'
import UpdateBookScreen from '../Screen/UpdateBookScreen'
import LogoutScreen from '../Screen/LogoutScreen'
import UserMessageScreen from '../Screen/UserMessageScreen'
import QuestionScreen from '../Screen/QuestionScreen'
import ClosedMessageScreen from '../Screen/ClosedMessageScreen'

const Drawer = createDrawerNavigator()




const RootNavigator = () => {

  const {user, userSuccess, userError, message} = useSelector(state => state.Users)
  const dispatch = useDispatch()

  const loginDispatch = useCallback( async () => {
   await  dispatch(UserLogin())
   if(user) {
    console.log(user)
   }
  }, [user, dispatch])

  useEffect(() => {
  // loginDispatch()
  }, [dispatch])

  return (
    <Drawer.Navigator initialRouteName='Main'>
        <Drawer.Screen name='Main'  component={MainNavigator}></Drawer.Screen>
       {!user && (
        <>
       
        <Drawer.Screen name='Login' component={LoginScreen}></Drawer.Screen>
        <Drawer.Screen name='Register' component={RegisterScreen}></Drawer.Screen>
        
        </>
       )}
        {user && user.role == "ADMIN" && (
          <> 
            <Drawer.Screen name='AddBook' component={AddBookScreen}></Drawer.Screen>
            <Drawer.Screen name='UpdateBook' component={UpdateBookScreen}></Drawer.Screen>
            <Drawer.Screen name='Logout' component={LogoutScreen}></Drawer.Screen>
            <Drawer.Screen name="question" component={QuestionScreen}></Drawer.Screen>
            <Drawer.Screen name="Message" component={ClosedMessageScreen}></Drawer.Screen>
          </>
        )}
        {user && user.role == "USER" && (
          <>
          <Drawer.Screen name='Checkout'  component={CheckoutScreen}></Drawer.Screen>
          <Drawer.Screen name='History'  component={HistoryScreen}></Drawer.Screen>
          <Drawer.Screen name='Message' component={UserMessageScreen}></Drawer.Screen>
          <Drawer.Screen name='Logout' component={LogoutScreen}></Drawer.Screen>
          </>
        )}
        
         
         
         
     </Drawer.Navigator>
  )

}

export default RootNavigator

