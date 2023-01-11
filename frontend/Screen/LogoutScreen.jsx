import { Alert, SafeAreaView, StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { useTailwind } from 'tailwind-rn/dist'
import { Button } from '@rneui/base'
import { useDispatch } from 'react-redux'
import { useNavigation } from '@react-navigation/native'
import AsyncStorage from '@react-native-async-storage/async-storage'
import { Logout } from '../Actions/UserAction'

const LogoutScreen = () => {
    const tw = useTailwind()
    const dispatch = useDispatch()
    const navigation = useNavigation()

    const LogoutFunction = async () => {
        try {
            await dispatch(Logout())
           await Alert.alert("logout Successfully")
            navigation.navigate("Main", {screen: "Home"})
        } catch (err) {
            Alert.alert("logout failed")
        }
    }

  return (
    <SafeAreaView style={tw('flex-1 items-center justify-center')}>
      <Button activeOpacity={0.5} onPress={LogoutFunction} buttonStyle={tw(' rounded-full px-4 py-2 text-2xl text-white bg-blue-500 mx-auto')} title="Log Out"></Button>
    </SafeAreaView>
  )
}

export default LogoutScreen

const styles = StyleSheet.create({})