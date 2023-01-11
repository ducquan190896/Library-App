import { ActivityIndicator, FlatList, StyleSheet, Text, View } from 'react-native'
import React, { useCallback, useEffect, useState } from 'react'
import { useNavigation } from '@react-navigation/native'
import { useDispatch, useSelector } from 'react-redux'
import { getCheckoutByAuth } from '../Actions/CheckoutAction'
import { useTailwind } from 'tailwind-rn/dist'
import { SafeAreaView } from 'react-native-safe-area-context'
import CheckoutCard from '../component/CheckoutCard'

const CheckoutScreen = () => {
  const navigation = useNavigation()
  const dispatch = useDispatch()
  const {checkouts, checkout, checkoutSuccess, checkoutError, message} = useSelector(state => state.Checkouts)
  const [isLoading, setIsLoading] = useState(false)
  const tw = useTailwind()

  const loadCheckouts = useCallback(async () => {
  await  dispatch(getCheckoutByAuth())
  }, [dispatch, checkouts])

  useEffect(() => {
   setIsLoading(true) 
   loadCheckouts().then(() => setIsLoading(false))
  }, [dispatch])

  if(isLoading) {
    return <ActivityIndicator size="large" color="blue" ></ActivityIndicator>
  }
  if(!isLoading && checkouts.length == 0) {
    return <Text style={tw('text-2xl font-bold mx-auto text-center text-blue-500')}>No Checkouts</Text>
  }

  return (
   <SafeAreaView style={tw('flex-1')}>
    <Text style={tw('text-2xl text-blue-500 font-bold mx-auto mb-2')}>Your Loans</Text>
      {checkouts && checkouts.length > 0 && (
        <FlatList
        style={tw('flex-1 px-2 my-2')}
        data={checkouts}
        keyExtractor={item => item.id}
        renderItem={({item}) => <CheckoutCard item={item}></CheckoutCard>}
        >
        </FlatList>
      )}
   </SafeAreaView>
  )
}

export default CheckoutScreen

const styles = StyleSheet.create({})