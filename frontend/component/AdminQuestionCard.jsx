import { Alert, StyleSheet, Text, TextInput, View } from 'react-native'
import React, { useState } from 'react'
import { useTailwind } from 'tailwind-rn/dist'
import { useDispatch } from 'react-redux'
import { Button } from '@rneui/base'
import { addAnswer, getAdminCloseMessages } from '../Actions/MessageAction'

const AdminQuestionCard = ({item}) => {
    const tw = useTailwind()
    const dispatch = useDispatch()
    const [answerStatus, setAnswerStatus] = useState(false)
    const [buttonShown, setButtonShown] = useState(true)    
    const [answer, setAnswer] = useState(null)

    const showForm = async() => {
       await setAnswerStatus(prev => !prev)
        
            setButtonShown(prev => !prev)
        
    }

    const submitFunction = async() => {
       if(answer) {
        await dispatch(addAnswer({messageId: item.id, answer: answer}))
       
        await  setButtonShown(prev => !prev)
       setAnswerStatus(prev => !prev)
       Alert.alert("responsed successfully")
       } else {
        Alert.alert("please response to the question")
       }
    }

    if(item) {
       return (
           <View style={tw('w-full my-2 items-start justify-start py-2 px-2 bg-white rounded-lg')}>
             <Text style={tw('mb-2 text-lg font-bold text-blue-500')}>case #{item.id}: {item.title}</Text>
             <Text style={tw('mb-2 text-base font-bold text-blue-500')}>{item.username}</Text>
             <Text style={tw('mb-2 text-sm text-gray-500')}>{item.title}</Text>
             {buttonShown && <Button onPress={showForm} title="Response" buttonStyle={tw('text-lg font-bold text-white bg-blue-500 px-2 py-2 rounded-full my-2')}></Button>}
             {answerStatus && (
                <View style={tw('w-full my-2')}>
                    <TextInput onChangeText={text => setAnswer(text)} value={answer} placeholder="response..." style={tw('rounded-full w-full  mb-4 py-2 px-4 w-full bg-gray-200 text-base')}></TextInput>
                    <Button onPress={submitFunction} title="Submit" buttonStyle={tw('text-lg font-bold text-white bg-blue-500 px-2 py-2 rounded-full my-2')}></Button>
                </View>
             )}
           </View>
         )
    }   
   
    return <Text style={tw('mx-auto my-2 text-2xl font-bold text-blue-500')}>No Pending Message</Text>
}

export default AdminQuestionCard

const styles = StyleSheet.create({})