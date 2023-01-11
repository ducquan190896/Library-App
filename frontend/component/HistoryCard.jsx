import { Alert, Image, StyleSheet, Text, View } from 'react-native'
import React, { useCallback, useEffect } from 'react'
import { useTailwind } from 'tailwind-rn/dist'
import { useDispatch, useSelector } from 'react-redux'
import { Button } from '@rneui/base'
import { extendCheckout, resetCheckouts, returnBook } from '../Actions/CheckoutAction'

const HistoryCard = ({item}) => {
    const tw = useTailwind()
   
    const {histories, historySuccess, historyError} = useSelector(state => state.Histories)


  return (
    <View style={tw('w-full my-2 flex-row rounded-lg items-center justify-between mx-2 bg-white')}>
        <Image style={tw('h-64 w-1/2 rounded-lg ')} source={item.book.imgUrl ? {uri: item.book.imgUrl} : require("../image/book-luv2code-1000.png")}></Image>
        
      <View style={tw('w-1/2 items-center justify-start px-2')}>
      <Text style={tw('text-base text-blue-500 font-bold  mb-2 text-center')}> {item.book.title}</Text>
        <Text style={tw('text-base text-blue-500 font-bold  mb-2 text-center')}> {item.book.author}</Text>
        <Text style={tw('text-sm text-blue-400 font-bold mx-auto mb-2 text-center')}> {item.book.description}</Text>
      <Text style={tw('text-sm text-zinc-700 font-bold mx-auto mb-2 text-center')}>return time: {item.checkoutTime}</Text>
        <Text style={tw('text-sm text-zinc-700 font-bold mx-auto mb-2 text-center')}>return time: {item.returnTime}</Text>
        
      </View>
      
    </View>
  )
}

export default HistoryCard

const styles = StyleSheet.create({})