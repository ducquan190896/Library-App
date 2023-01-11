
import { ActivityIndicator, Alert, FlatList, KeyboardAvoidingView, ScrollView,  Text, TextInput, View } from 'react-native'
import React, { useCallback, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigation } from '@react-navigation/native'
import { addQuestion, getAdminCloseMessages, getAdminOpenMessages, getAuthCloseMessages, getAuthOpenMessages, resetMessage } from '../Actions/MessageAction'
import { SafeAreaView } from 'react-native-safe-area-context'
import { useTailwind } from 'tailwind-rn/dist'
import OpenMessageCard from '../component/OpenMessageCard'
import { Button, Divider } from '@rneui/base'
import CloseMessageCard from '../component/CloseMessageCard'
import AdminQuestionCard from '../component/AdminQuestionCard'

const QuestionScreen = () => {
    const dispatch = useDispatch()
    const navigation = useNavigation()
    const {closeMessages, closeMessage, openMessages, openMessage, messageSuccess, messageError} = useSelector(state => state.Messages)
    const [isLoading, setIsLoading] = useState(false)
    
    const tw = useTailwind()
  
    const loadMessages = useCallback(async () => {
      await dispatch(getAdminOpenMessages())
      await dispatch(getAdminCloseMessages())
    }, [dispatch, openMessages, closeMessages])
  
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
        
       
       
        <Text style={tw('mx-auto my-2 text-2xl font-bold text-blue-500')}>Pending Messages</Text>
      {openMessages && openMessages.length > 0 &&  openMessages.map((item) => <AdminQuestionCard key={item.id} item={item}></AdminQuestionCard>)
   
      }
        </ScrollView>
      </SafeAreaView>
    )
}

export default QuestionScreen

