import { ActivityIndicator, Alert, FlatList, KeyboardAvoidingView, ScrollView, StyleSheet, Text, TextInput, View } from 'react-native'
import React, { useCallback, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigation } from '@react-navigation/native'
import {  getAdminCloseMessages, resetMessage } from '../Actions/MessageAction'
import { SafeAreaView } from 'react-native-safe-area-context'
import { useTailwind } from 'tailwind-rn/dist'
import CloseMessageCard from '../component/CloseMessageCard'

const ClosedMessageScreen = () => {
    const dispatch = useDispatch()
    const navigation = useNavigation()
    const {closeMessages, closeMessage, openMessages, openMessage, messageSuccess, messageError} = useSelector(state => state.Messages)
    const [isLoading, setIsLoading] = useState(false)
    
    const tw = useTailwind()
  
    const loadMessages = useCallback(async () => {
      await dispatch(getAdminCloseMessages())
      await dispatch(getAdminCloseMessages())
    }, [dispatch, closeMessages])
  
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
  
 
    
    if(isLoading) {
      return <ActivityIndicator size="large" color="blue"></ActivityIndicator>
    }
  
    return (
      <SafeAreaView style={tw('w-full bg-gray-200')}>
        <ScrollView style={tw('w-full')}>
        
       
       
        <Text style={tw('mx-auto my-2 text-2xl font-bold text-blue-500')}>Answered Messages</Text>
        {closeMessages && closeMessages.length > 0 && closeMessages.map((item) => <CloseMessageCard item={item} key={item.id}></CloseMessageCard>)
     
        }
        </ScrollView>
      </SafeAreaView>
    )
}

export default ClosedMessageScreen

