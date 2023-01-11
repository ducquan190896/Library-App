import { StyleSheet, Text, View } from 'react-native'
import React, { useEffect } from 'react'
import { useTailwind } from 'tailwind-rn/dist'
import Rating from './Rating'

const ReviewCard = ({item}) => {
    const tw = useTailwind()
   
  return (
    <View style={tw(' w-full rounded-lg border border-gray-300 py-2 my-2 px-6')}>
        <Text style={tw('text-base font-bold text-blue-500')}>{item.username}</Text>
      <Text style={tw('text-base text-blue-400')}>{item.reviewDescription}</Text>
      <Text style={tw('text-base text-gray-500')}>{item.date}</Text>
      <Rating rate={item.rating}></Rating>
    </View>
  )
}

export default ReviewCard

const styles = StyleSheet.create({})