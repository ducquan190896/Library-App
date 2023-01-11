import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { useTailwind } from 'tailwind-rn/dist'

const OpenMessageCard = ({item}) => {
    const tw = useTailwind()

 if(item) {
    return (
        <View style={tw('w-full my-2 items-start justify-start py-2 px-2 bg-white rounded-lg')}>
          <Text style={tw('mb-2 text-lg font-bold text-blue-500')}>case #{item.id}: {item.title}</Text>
          <Text style={tw('mb-2 text-base font-bold text-blue-500')}>{item.username}</Text>
          <Text style={tw('mb-2 text-sm text-gray-500')}>{item.title}</Text>
        </View>
      )
 }   

 return <Text style={tw('mx-auto my-2 text-2xl font-bold text-blue-500')}>No Pending Message</Text>
}

export default OpenMessageCard

const styles = StyleSheet.create({})