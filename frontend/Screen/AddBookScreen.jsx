import { Alert, KeyboardAvoidingView, StyleSheet, Text, TextInput, View } from 'react-native'
import React, { useState } from 'react'
import { SafeAreaView } from 'react-native-safe-area-context'
import { useTailwind } from 'tailwind-rn/dist'
import { useDispatch } from 'react-redux'
import { useNavigation } from '@react-navigation/native'
import * as ImagePicker from 'expo-image-picker';
import { Button } from '@rneui/base'
import AsyncStorage from '@react-native-async-storage/async-storage'
import { addBook, loadBooks } from '../Actions/BookAction'


const AddBookScreen = () => {
  const tw = useTailwind()
  const dispatch = useDispatch()
  const navigation = useNavigation()
  const [imageURL, setImageURL] =  useState(null)
  const [title, setTitle] = useState(null)
  const [author, setAuthor] = useState(null)
  const [description, setDescription] = useState(null)
  const [copies, setCopies] = useState(0)
  const [copiesAvailable, setCopiesAvailable] = useState(0)
  const [category, setCategory] = useState(null)

  
  const uploadImage = async  () => {
    const images = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [4, 3],
      quality: 1,
    })
    console.log(images)

    if(!images.canceled) {
      console.log(images.assets[0])
      const formdata = new FormData()
      formdata.append("file", {
        uri: images.assets[0].uri,
        type: images.assets[0].type,
        name: images.assets[0].fileName
      })
      const token = await AsyncStorage.getItem("token")
      console.log(token)

      const res = await fetch("http://10.0.2.2:8080/api/images/uploadimage2", {
        method: "POST",
        headers: {
          "Content-Type": "multipart/form-data",
          "Authorization": token
        },
        body: formdata
      })
      const uriResponse = await res.json()
      console.log(uriResponse)

      setImageURL("http://10.0.2.2:8080" + uriResponse)
    }
  } 

  const submitFunction = async () => {
    try {
      if(title && author && description && category && copies && copiesAvailable) {
        const formdata = {
          title,
          author,
          description,
          category,
          copies,
          copiesAvailable
        }
        if(imageURL) {
          formdata.imgUrl = imageURL
        }
        await dispatch(addBook(formdata))
        await dispatch(loadBooks())
        setTitle(null)
        setAuthor(null)
        setDescription(null)
        setCategory(null)
        setCopies(0)
        setCopiesAvailable(0)
        setImageURL(null)
        Alert.alert("adding successfully")
        navigation.navigate("Main", {screen: "Home"})

      } else {
        Alert.alert("please add more information...")
      }
    } catch (err) {
      Alert.alert("creating failed")
    }
  }


  return (
    <SafeAreaView style={tw('flex-1 px-4')}>
      <KeyboardAvoidingView style={tw('flex-1')}>
      <Text style={tw('text-2xl font-bold text-blue-500 mx-auto my-2')}>New Book</Text>
    
    <TextInput onChangeText={text => setTitle(text)} value={title} placeholder="title..." style={tw('rounded-full w-full  mb-4 py-2 px-4 w-full bg-gray-300 text-base')}></TextInput>
    <TextInput onChangeText={text => setAuthor(text)} value={author} placeholder="author..." style={tw('rounded-full w-full  mb-4 py-2 px-4 w-full bg-gray-300 text-base')}></TextInput>
    <TextInput onChangeText={text => setDescription(text)} value={description} placeholder="description..." style={tw('rounded-full w-full  mb-4 py-2 px-4 w-full bg-gray-300 text-base')}></TextInput>
    <TextInput onChangeText={text => setCategory(text)} value={category} placeholder="category..."  style={tw('rounded-full w-full  mb-4 py-2 px-4 w-full bg-gray-300 text-base')}></TextInput>
    <TextInput onChangeText={text => setCopies(text)} value={copies} placeholder="copies..." keyboardType='numeric' style={tw('rounded-full w-full  mb-4 py-2 px-4 w-full bg-gray-300 text-base')}></TextInput>
    <TextInput onChangeText={text => setCopiesAvailable(text)} keyboardType='numeric' value={copiesAvailable} placeholder="available copies..." style={tw('rounded-full w-full  mb-4 py-2 px-4 w-full bg-gray-300 text-base')}></TextInput>

    <Button onPress={uploadImage} buttonStyle={tw('w-full mx-auto rounded-full mb-8 py-2 text-white bg-blue-500')} title="Add Image"></Button>
    <Button onPress={submitFunction} buttonStyle={tw('w-full mx-auto rounded-full  py-2 text-white bg-blue-500')} title="Create Book"></Button>
      </KeyboardAvoidingView>
    </SafeAreaView>
  )
}

export default AddBookScreen

