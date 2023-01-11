import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { AntDesign } from '@expo/vector-icons'; 
import { useTailwind } from 'tailwind-rn/dist';
const Rating = ({rate}) => {
    const tw = useTailwind()
  return (
    <View style={tw('flex flex-row items-center justify-start')}>
      {rate == 1 && <>
        <AntDesign name="star" size={18} color="blue" style={tw('mr-2')} />
      </>}
      {rate == 2 && <>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
      </>}
      {rate == 3 && <>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
      </>}
      {rate == 4 && <>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
      </>}
      {rate == 5 && <>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
        <AntDesign name="star" size={18} color="blue"  style={tw('mr-2')}/>
      </>}
      
    </View>
  )
}

export default Rating

const styles = StyleSheet.create({})