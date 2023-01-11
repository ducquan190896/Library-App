import { ActivityIndicator, FlatList, StyleSheet, Text, View } from 'react-native'
import React, { useCallback, useEffect, useState } from 'react'
import { useNavigation } from '@react-navigation/native'
import { useDispatch, useSelector } from 'react-redux'
import { getCheckoutByAuth } from '../Actions/CheckoutAction'
import { useTailwind } from 'tailwind-rn/dist'
import { SafeAreaView } from 'react-native-safe-area-context'

import HistoryCard from '../component/HistoryCard'
import { getHistoriesByAuth, resetHistory } from '../Actions/HistoryAction'

const HistoryScreen = () => {
  const navigation = useNavigation()
  const dispatch = useDispatch()
  const {histories, historySuccess, historyError} = useSelector(state => state.Histories)
  const [isLoading, setIsLoading] = useState(false)
  const tw = useTailwind()

  const loadHistory = useCallback(async() => {
    await dispatch(getHistoriesByAuth())
  }, [dispatch, histories])
  //   const loadHistory = async() => {
  //   await dispatch(getHistoriesByAuth())
  // }

  useEffect(() => {
    setIsLoading(true)
    loadHistory().then(() => setIsLoading(false))
  }, [dispatch])

  useEffect(() => {
    if(!historyError || !historySuccess) {
      dispatch(resetHistory())
    }
  }, [historySuccess, dispatch, historyError])

  if(isLoading) {
    return <ActivityIndicator size="large" color="blue" ></ActivityIndicator>
  }
  if(!isLoading && histories.length == 0) {
    return <Text style={tw('text-2xl font-bold mx-auto text-center text-blue-500')}>No histories</Text>
  }

  return (
   <SafeAreaView style={tw('flex-1')}>
    <Text style={tw('text-2xl text-blue-500 font-bold mx-auto mb-2')}>Your Histories</Text>
      {histories && histories.length > 0 && (
        <FlatList
        style={tw('flex-1 px-2 my-2')}
        data={histories}
        keyExtractor={item => item.id}
        renderItem={({item}) => <HistoryCard item={item}></HistoryCard>}
        >
        </FlatList>
      )}
   </SafeAreaView>
  )
}


export default HistoryScreen

const styles = StyleSheet.create({})