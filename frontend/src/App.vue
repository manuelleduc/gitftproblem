<template>
    <div id="app">
        <img alt="Vue logo" src="./assets/logo.png">
        <GiftResult />
        <GiftForm />
    </div>
</template>

<script>
    import GiftForm from './components/GiftForm.vue'
    import GiftResult from "./components/GiftResult";


    export default {
        name: 'app',
        components: {
            GiftForm, GiftResult
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>
