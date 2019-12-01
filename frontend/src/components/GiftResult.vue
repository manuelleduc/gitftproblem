<template>
    <div>
        <div v-if="result.attributions.length === 0">No result.</div>
        <div v-if="result.attributions.length > 0">Results.</div>
    </div>
</template>

<script>
    import {mapState} from 'vuex';

    export default {
        name: "GiftResult",
        computed: mapState({result: state => state.result})
    }
</script>

<style scoped>

</style>