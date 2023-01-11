import { ActivityIndicator, FlatList, ScrollView, StyleSheet, Text, View } from 'react-native'
import React, { useCallback, useEffect, useLayoutEffect, useState } from 'react'
import { SafeAreaView } from 'react-native-safe-area-context';
import { useTailwind } from 'tailwind-rn/dist';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigation } from '@react-navigation/native';
import { loadBooks, resetBooks } from '../Actions/BookAction';
import BookCard from '../component/BookCard';
import UpdateBookCard from '../component/UpdateBookCard';

const UpdateBookScreen = () => {
    const tw = useTailwind()
    const [text, setText] = useState(null);
    const {book, books, bookSuccess, bookError, message, updateBook, updateStatus} = useSelector(state => state.Books)
    const dispatch = useDispatch()
    const [isLoading, setIsLoading] = useState(false)
    const [isError, setIsError] = useState(false)
    const navigation = useNavigation()

    const loadBooksFunction = useCallback(async () => {
    await dispatch(loadBooks())
  }, [dispatch, books])

    useEffect(() => {
    setIsLoading(true)
     loadBooksFunction().then(() => setIsLoading(false))
  }, [dispatch])

    useEffect(() => {
        if(bookSuccess || bookError) {
            dispatch(resetBooks())
        }
    }, [bookSuccess, bookError, books, book])
  return (
    <SafeAreaView style={tw('flex-1 bg-zinc-300')}>
    <Text style={tw('mx-auto my-2 text-2xl font-bold text-blue-500')}>Update Books</Text>
   
    { books && books.length > 0 &&   (
     <FlatList
    style={tw('flex-1 px-2')}
     data={books}
     keyExtractor={item => item.id}
    renderItem={({item}) => <UpdateBookCard item={item}></UpdateBookCard>}
     ></FlatList>
   )}
   
 
   </SafeAreaView>
  )
}

export default UpdateBookScreen

const styles = StyleSheet.create({})