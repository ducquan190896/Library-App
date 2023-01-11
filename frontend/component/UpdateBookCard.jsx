import { Alert, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React from 'react'
import { useTailwind } from 'tailwind-rn/dist'
import { Image } from '@rneui/themed'
import { Button } from '@rneui/base'
import { useNavigation } from '@react-navigation/native'
import { useDispatch } from 'react-redux'
import { decreaseBook, increaseBook } from '../Actions/BookAction'


const UpdateBookCard = ({item}) => {
    const tw = useTailwind()
    const navigation = useNavigation()
    const dispatch = useDispatch()

    const increaseFunction = async () => {
        try {
            await dispatch(increaseBook(item.id))
            Alert.alert("Increased successfully")
        } catch (err) {
            Alert.alert("Increase failed")
        }
    }
    const decreaseFunction = async () => {
        try {
            await dispatch(decreaseBook(item.id))
            Alert.alert("Decreased successfully")
        } catch (err) {
            Alert.alert("Decrease failed")
        }
    }
  
  

  return (
    <TouchableOpacity activeOpacity={0.5} style={tw('w-full mb-4 rounded-lg bg-white flex-row items-center justify-between')}>
    <Image containerStyle={tw('w-1/2 h-64 rounded-lg')} source={item.imgUrl ? {uri: item.imgUrl} : require("../image/book-luv2code-1000.png")}></Image>
     <View style={tw('w-1/2 flex items-center justify-center')}>
      <Text style={tw('text-blue-500 text-lg font-bold mb-4 mx-auto')}>{item.title}</Text>
      <Text style={tw('text-blue-500 text-base  mb-4 mx-auto')}>{item.author}</Text>
      <View style={tw('flex-row px-2 items-center justify-between w-full')}>
        <Text style={tw('text-base text-blue-400 font-bold')}>{item.copies} Copies</Text>
        <Text style={tw('text-base text-blue-400 font-bold')}>{item.copiesAvailable} Available</Text>          
    </View>
      <Button onPress={increaseFunction} title="Increase" buttonStyle={tw('px-2 py-2 text-center text-base font-bold text-zinc-400 bg-blue-500 mt-6 rounded-full')}></Button>
      <Button onPress={decreaseFunction} title="Decrease" buttonStyle={tw('px-2 py-2 text-center text-base font-bold text-zinc-400 bg-blue-500 mt-6 rounded-full')}></Button>
    </View>
   </TouchableOpacity>
  )
}

export default UpdateBookCard

const styles = StyleSheet.create({})