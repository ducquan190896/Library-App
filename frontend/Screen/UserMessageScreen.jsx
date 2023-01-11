import { ActivityIndicator, Alert, FlatList, KeyboardAvoidingView, ScrollView, StyleSheet, Text, TextInput, View } from 'react-native'
import React, { useCallback, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigation } from '@react-navigation/native'
import { addQuestion, getAuthCloseMessages, getAuthOpenMessages, resetMessage } from '../Actions/MessageAction'
import { SafeAreaView } from 'react-native-safe-area-context'
import { useTailwind } from 'tailwind-rn/dist'
import OpenMessageCard from '../component/OpenMessageCard'
import { Button, Divider } from '@rneui/base'
import CloseMessageCard from '../component/CloseMessageCard'

const UserMessageScreen = () => {
  const dispatch = useDispatch()
  const navigation = useNavigation()
  const {closeMessages, closeMessage, openMessages, openMessage, messageSuccess, messageError} = useSelector(state => state.Messages)
  const [isLoading, setIsLoading] = useState(false)
  const [addingStatus, setAddingStatus] = useState(false)
  const [title, setTitle] = useState(null)
  const [question, setQuestion] = useState(null)

  const tw = useTailwind()

  const loadMessages = useCallback(async () => {
    await dispatch(getAuthOpenMessages())
    await dispatch(getAuthCloseMessages())
  }, [dispatch, closeMessages, openMessages])

  useEffect(() => {
    setIsLoading(true)
    loadMessages().then(() => setIsLoading(false))
  }, [dispatch])
  useEffect(() => {
    if(closeMessages) {
      console.log(closeMessages)
    }
    if(openMessages) {
      console.log(openMessages)
    }
    if(messageSuccess ||messageError) {
      dispatch(resetMessage())
    }
  }, [dispatch, messageSuccess, messageError])

  const AddMessageFunction =  () => {
    setAddingStatus(prev => setAddingStatus(!prev))
  }
  const submitQuestion =  async () => {
    try {
     await  dispatch(addQuestion({title, question}))
     await loadMessages()
      setTitle(null)
      setQuestion(null)
      setAddingStatus(prev => !prev)
      Alert.alert("adding messageSuccessfully ")
    } catch (err) {
      Alert.alert("adding failed ")
    }
  }

  if(isLoading) {
    return <ActivityIndicator size="large" color="blue"></ActivityIndicator>
  }

  return (
    <SafeAreaView style={tw('w-full bg-gray-200')}>
      <ScrollView style={tw('w-full')}>
      <Text style={tw('mx-auto my-2 text-2xl font-bold text-blue-500')}>Pending Messages</Text>
      {openMessages && openMessages.length > 0 &&  openMessages.map((item) => <OpenMessageCard key={item.id} item={item}></OpenMessageCard>)
   
      }
      <Button onPress={AddMessageFunction} buttonStyle={tw('mx-2 rounded-lg px-4 py-2 bg-blue-500 text-lg font-bold text-white mt-4')} title="Add Message"></Button>
        {addingStatus && (
              <View style={tw('w-full my-2 mt-4')}>
              <TextInput onChangeText={text => setTitle(text)} value={title} placeholder="title..." style={tw('rounded-full mb-4 py-2 px-4 w-full bg-white text-base')}></TextInput>
              <TextInput onChangeText={text => setQuestion(text)} value={question} placeholder="message..." style={tw('rounded-full py-2 px-4 w-full bg-white text-base')}></TextInput>
              <Button onPress={submitQuestion} buttonStyle={tw(' w-1/3 mx-2 rounded-full px-4 py-2 mx-auto bg-blue-500 text-lg font-bold text-white mt-4')} title="Submit"></Button>
              </View>
        )}

      <Divider  width={4}  style={tw('w-full my-4 text-gray-200')}></Divider>
      <Text style={tw('mx-auto my-2 text-2xl font-bold text-blue-500')}>Answered Messages</Text>
      {closeMessages && closeMessages.length > 0 && closeMessages.map((item) => <CloseMessageCard item={item} key={item.id}></CloseMessageCard>)
   
      }
      </ScrollView>
    </SafeAreaView>
  )
}

export default UserMessageScreen

const styles = StyleSheet.create({})