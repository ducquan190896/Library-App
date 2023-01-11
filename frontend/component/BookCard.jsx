import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React from 'react'
import { useTailwind } from 'tailwind-rn/dist'
import { Image } from '@rneui/themed'
import { Button } from '@rneui/base'
import { useNavigation } from '@react-navigation/native'

const BookCard = ({item}) => {
    const tw = useTailwind()
    const navigation = useNavigation()

    const detailButton = () => {
        // navigation.navigate("BookDetail", {
        //     id: item.id
        // })
        navigation.navigate("BookDetail", {item: item})
    }

  return (
   <TouchableOpacity activeOpacity={0.5} style={tw('w-full mb-4 rounded-lg bg-white flex-row items-center justify-between')}>
    <Image containerStyle={tw('w-1/2 h-64 rounded-lg')} source={item.imgUrl ? {uri: item.imgUrl} : require("../image/book-luv2code-1000.png")}></Image>
     <View style={tw('w-1/2 flex items-center justify-center')}>
      <Text style={tw('text-blue-500 text-lg font-bold mb-4 mx-auto')}>{item.title}</Text>
      <Text style={tw('text-blue-500 text-base  mb-4 mx-auto')}>{item.author}</Text>
      <Button onPress={detailButton} title="Details" buttonStyle={tw('w-28 h-14 text-center text-lg font-bold text-zinc-400 bg-blue-500 mt-6 rounded-full')}></Button>
    </View>
   </TouchableOpacity>
  )
}

export default BookCard

const styles = StyleSheet.create({})