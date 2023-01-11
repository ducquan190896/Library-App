import { Alert, Image, StyleSheet, Text, View } from 'react-native'
import React, { useCallback, useEffect } from 'react'
import { useTailwind } from 'tailwind-rn/dist'
import { useDispatch, useSelector } from 'react-redux'
import { Button } from '@rneui/base'
import { extendCheckout, resetCheckouts, returnBook } from '../Actions/CheckoutAction'

const CheckoutCard = ({item}) => {
    const tw = useTailwind()
    const dispatch = useDispatch()
    const {checkouts, checkout, checkoutSuccess, checkoutError, message} = useSelector(state => state.Checkouts)
    const loadCheckout = useCallback(async () => {
        await  dispatch(getCheckoutByAuth())
        }, [dispatch, checkouts])

    useEffect(() => {
        if(checkoutSuccess) {
            dispatch(resetCheckouts())
        }
    }, [dispatch])
 

    const returBookFunction = () => {
      try {
       dispatch(returnBook(item.id))
      
        Alert.alert("return book successfully")
        
      } catch (err) {
        Alert.alert("return book failed")
      }

    }
    
    const extendBookReturnFunction=  () => {
        try {
           dispatch(extendCheckout(item.id))
       
          Alert.alert("extend successfully")
          
        } catch (err) {
          Alert.alert("extend failed")
        }
  
      }

  return (
    <View style={tw('w-full my-2 flex-row rounded-lg items-center justify-between mx-2 bg-white')}>
        <Image style={tw('h-64 w-1/2 rounded-lg ')} source={item.book.imgUrl ? {uri: item.book.imgUrl} : require("../image/book-luv2code-1000.png")}></Image>
      <View style={tw('w-1/2 items-center justify-start px-2')}>
        <Text style={tw('text-sm text-blue-500 font-bold mx-auto mb-2 text-center')}>due date: {item.returnTime}</Text>
        <Button onPress={extendBookReturnFunction} buttonStyle={tw(' bg-blue-500 text-white font-bold rounded-full my-4 mx-4')} title="Extend days"></Button>
        <Button onPress={returBookFunction} buttonStyle={tw(' bg-blue-500 text-white font-bold rounded-full my-4 mx-4')} title="Return Book"></Button>
      </View>
    </View>
  )
}

export default CheckoutCard

const styles = StyleSheet.create({})