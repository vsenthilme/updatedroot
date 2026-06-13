importScripts('https://www.gstatic.com/firebasejs/9.1.3/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.1.3/firebase-messaging-compat.js');


firebase.initializeApp({
  apiKey: "AIzaSyBO3lcXwVMw_-hINr0HM5iqcfQUCZzuMiY",
  authDomain: "overc360.firebaseapp.com",
  projectId: "overc360",
  storageBucket: "overc360.appspot.com",
  messagingSenderId: "955737712957",
  appId: "1:955737712957:web:afcfa64ce8c9e326727d54",
  measurementId: "G-52P79GEPBG",
  vapidKey: 'BHcuGu6psAxJdbfI7oXz3fZHqN2fe8EvwW6pOyKB1ZvKkhZ8H3DOSLbfIcAiVUA_NFrTqcMknITfSPZ0ZyVTtKY'
});
const messaging = firebase.messaging();

