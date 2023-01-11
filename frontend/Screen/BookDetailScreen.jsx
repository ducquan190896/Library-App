import { ActivityIndicator, Alert, ScrollView, StyleSheet, Text, View } from 'react-native'
import React, { useCallback, useLayoutEffect, useState } from 'react'
import { useEffect } from 'react'
import { useNavigation, useRoute } from '@react-navigation/native'
import { SafeAreaView } from 'react-native-safe-area-context'
import { Button, Image } from '@rneui/base'
import { useTailwind } from 'tailwind-rn/dist'
import { useDispatch, useSelector } from 'react-redux'
import { getreviewByBook } from '../Actions/ReviewAction'
import ReviewCard from '../component/ReviewCard'
import ReviewForm from '../component/ReviewForm'
import { AddCheckout, getCheckoutByAuth } from '../Actions/CheckoutAction'

const BookDetailScreen = () => {
    const route = useRoute()
    const {item} = route.params
    const navigation = useNavigation()
    const tw = useTailwind()
    const {user, userSuccess, userError} = useSelector(state => state.Users)
    const dispatch = useDispatch()
    const {reviews, review, reviewSuccess, reviewError, message : reviewMessage} = useSelector(state => state.Reviews);
    const {checkouts, checkout, checkoutSuccess, checkoutError} = useSelector(state => state.Checkouts)
  const [isLoading, setIsLoading] = useState(false)
  const [reviewStatus, setReviewStatus] = useState(false)
  const [checkoutStatus, setCheckoutStatus] = useState(true)

    const loadReviews = useCallback( async () => {
     await dispatch(getreviewByBook(item.id))
    }, [dispatch, reviews])

    const loadCheckouts = useCallback(async () => {
      await dispatch(getCheckoutByAuth())
    }, [dispatch, checkouts])

    useEffect(() => {  
       if(item) {
        console.log(item)
        setIsLoading(true)
        loadReviews().then(() => loadCheckouts()).then(() => setIsLoading(false))
        if(reviews) {
          console.log(reviews)
        }

       }
       
    }, [dispatch, useRoute])
    useEffect(() => {
      if(item && checkouts && checkouts.length > 0) {
        if(checkouts.filter(checko => checko.book.id == item.id).length > 0) {
          setCheckoutStatus(false)
        }
      }
    }, [dispatch, setCheckoutStatus, checkouts, item])

    const addCheckoutFunction = async() => {
      try {
        await dispatch(AddCheckout(item.id))
        await dispatch(getCheckoutByAuth())
        Alert.alert("checkout Successfully")
      } catch (err) {
        Alert.alert("checkout failed")
      }
      
    }

    if(isLoading) {
      return <ActivityIndicator size="large" color="blue"></ActivityIndicator>
    }

  
  return (
    <SafeAreaView style={tw('flex-1')}>
      {item && <ScrollView style={tw('flex-1')}>
        <View style={tw('flex-1 px-4 my-2 items-center')}>
          <Image containerStyle={tw('w-64 mb-4 h-64 rounded-lg')} source={item.imgUrl ? {uri: item.imgUrl} : require("../image/book-luv2code-1000.png")}></Image>
          <Text style={tw('text-2xl font-bold text-blue-500 mb-2')}>{item.title}</Text>
          <Text style={tw('text-lg font-bold text-blue-400 mb-2')}>{item.author}</Text>
          <Text style={tw('text-base text-zinc-700 mb-2')}>{item.description}</Text>
          <View style={tw('w-full mb-2 rounded-lg border border-zinc-700  px-4 py-2')}>
            <Text style={tw('text-lg font-bold text-blue-500 mb-4 mx-auto ')}>Available</Text>
            <View style={tw('flex-row px-2 items-center justify-between w-full')}>
              <Text style={tw('text-base text-blue-400 font-bold')}>{item.copies} Copies</Text>
              <Text style={tw('text-base mb-2 text-blue-400 font-bold')}>{item.copiesAvailable} Available</Text>          
            </View>
            {user && checkoutStatus && item.copiesAvailable > 0 && <Button onPress={addCheckoutFunction} buttonStyle={tw('text-lg w-1/3 rounded-full my-2 mx-auto font-bold text-white bg-blue-500')} title="Check Out"></Button> }

            {!user &&  item.copiesAvailable > 0 && <Button buttonStyle={tw('text-lg w-2/3 mx-auto font-bold text-white my-2 bg-blue-500')} onPress={() => navigation.navigate('Login')} title="Login"></Button>}

            {user && item.copiesAvailable > 0 && !checkoutStatus && <Text style={tw('text-base text-red-400 mx-auto')}>You already loaned this book</Text>}

            { item.copiesAvailable <= 0 && <Text style={tw('text-base text-red-400 mx-auto')}>No available books for Checkout</Text>}
          </View>
          
          <Text style={tw('text-2xl text-blue-500 font-bold mt-4 mb-2')}>Latest Review</Text>
          {user && <Button onPress={() => setReviewStatus(prev => !prev)} buttonStyle={tw('text-lg w-1/3 rounded-full my-2 mx-auto font-bold text-white bg-blue-500')} title="Leave Review"></Button>}
          {user && reviewStatus && <ReviewForm bookId={item.id} setReviewStatus={setReviewStatus}></ReviewForm>}
          {reviews && reviews.length > 0  && reviews.map( re => <ReviewCard key={re.id} item={re}></ReviewCard>)}
        </View>
 
        </ScrollView>}
    </SafeAreaView>
  )
}

export default BookDetailScreen

const styles = StyleSheet.create({})