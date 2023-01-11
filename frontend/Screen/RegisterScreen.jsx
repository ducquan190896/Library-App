import { Alert, StyleSheet, Text, View } from 'react-native'
import React, { useEffect, useLayoutEffect, useState } from 'react'
import {useTailwind} from 'tailwind-rn';
import { useDispatch, useSelector } from 'react-redux';
import { SafeAreaView } from 'react-native-safe-area-context';
import { TextInput } from 'react-native-gesture-handler';
import { Button } from '@rneui/base';
import { UserLogin, UserRegister } from '../Actions/UserAction';
import { useNavigation } from '@react-navigation/native';

const RegisterScreen = () => {
  
    const tw = useTailwind()
    const dispatch = useDispatch()
    const {user, userSuccess, userError, message} = useSelector(state => state.Users)
    const [username, setUsername] = useState(null)
    const [password, setPassword] = useState(null)
    const [email, setEmail] = useState(null)
    const [confirmedPassword, setConfirmedPassword] = useState(null)
    const navigation = useNavigation()
    useLayoutEffect(() => {
      navigation.setOptions({
        headerShown: false
      })
    })


    useEffect(() => {
      if(user && userSuccess) {
        console.log(user)
        Alert.alert("login successfully")
        navigation.navigate("Home")
       }
      
    }, [user, userSuccess, dispatch])
  
    const submitFunction = async () => {
  
      if(username && password && email && confirmedPassword) {
    
          await dispatch(UserRegister({email, username, password, confirmedPassword}))
       
      } else {
        Alert.alert("please type some thing")
      }
      
    }
  
  
    return (
      <SafeAreaView style={tw('flex-1')}>
        <View style={tw('w-full my-2 px-4 flex items-center justify-start')}>
          <Text style={tw(' w-full mb-2 font-bold text-blue-400 text-lg px-4')}>Email</Text>
          <TextInput onChangeText={text => setEmail(text)} value={email} placeholder="your email" style={tw('rounded-full py-2 px-4 w-full bg-slate-300 text-base')}></TextInput>
        </View>
        <View style={tw('w-full my-2 px-4 flex items-center justify-start')}>
          <Text style={tw(' w-full mb-2 font-bold text-blue-400 text-lg px-4')}>Username</Text>
          <TextInput onChangeText={text => setUsername(text)} value={username} placeholder="your username" style={tw('rounded-full py-2 px-4 w-full bg-slate-300 text-base')}></TextInput>
        </View>
        <View style={tw('w-full my-2 px-4 flex items-center justify-start')}>
          <Text style={tw(' w-full mb-2 font-bold text-blue-400 text-lg px-4')}>password</Text>
          <TextInput onChangeText={text => setPassword(text)} value={password} placeholder="your password" secureTextEntry={true} style={tw('rounded-full py-2 px-4 w-full bg-slate-300 text-base')}></TextInput>
        </View>
        <View style={tw('w-full my-2 px-4 flex items-center justify-start')}>
          <Text style={tw(' w-full mb-2 font-bold text-blue-400 text-lg px-4')}>confirm password</Text>
          <TextInput onChangeText={text => setConfirmedPassword(text)} value={confirmedPassword} placeholder="confirm password" secureTextEntry={true} style={tw('rounded-full py-2 px-4 w-full bg-slate-300 text-base')}></TextInput>
        </View>
        
        <Button onPress={submitFunction} title="Sign Up" buttonStyle={tw('mx-auto rounded-full px-4 py-2 mt-4 bg-sky-400 text-lg text-white')}></Button>
      </SafeAreaView>
  )
}

export default RegisterScreen

const styles = StyleSheet.create({})
