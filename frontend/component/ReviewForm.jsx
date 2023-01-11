import { Alert, StyleSheet, Text, View } from 'react-native'
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useTailwind } from 'tailwind-rn/dist'
import { Button, Input } from '@rneui/base'
import { addReview } from '../Actions/ReviewAction'

const ReviewForm = ({bookId, setReviewStatus}) => {
    const [rating, setRating] = useState(null)
    const [reviewDescription, setReviewDescription] = useState(null)
    const {reviews, review, reviewError, reviewSuccess} = useSelector(state => state.Reviews)
    const dispatch = useDispatch()
    const tw = useTailwind()

    const SubmitReview = async () => {
        if(rating && reviewDescription) {
            const obj = {
                rating: rating,
                reviewDescription: reviewDescription
            }
           await dispatch(addReview(bookId, obj))
           
           setRating(null)
            setReviewDescription(null)
           Alert.alert("review added successfully")
           setReviewStatus(pre => !pre) 
        } else {
            Alert.alert("please add enough information")
        }
    }

  return (
    <View style={tw('w-full items-center justify-center my-2')}>
      <Text style={tw('text-lg font-bold text-blue-500 mb-4')}>ReviewForm</Text>
      
      <Input value={rating}  keyboardType='numeric'  onChangeText={(text) => setRating(text)} inputStyle={tw('rounded-lg w-full py-2 pl-4 mb-2')} placeholder="your rating..."></Input>
      
      <Input value={reviewDescription}   onChangeText={(text) => setReviewDescription(text)} inputStyle={tw('rounded-lg w-full py-2 pl-4 mb-2')} placeholder="your review..."></Input>
      <Button buttonStyle={tw('text-lg w-1/3 rounded-full my-2 mx-auto font-bold text-white bg-blue-500')} title="Add Review" onPress={SubmitReview}></Button>
    </View>
  )
}

export default ReviewForm

const styles = StyleSheet.create({})