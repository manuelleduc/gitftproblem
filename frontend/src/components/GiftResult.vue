<template>
    <div>
        <div v-if="result.attributions.length === 0">No result.</div>
        <div v-if="result.attributions.length > 0">
            <table>
                <thead>
                <tr>
                    <td>From</td>
                    <td>To</td>
                </tr>
                </thead>
                <tbody>
                <tr v-for="attr in result.attributions" :key="attr">
                    <td>{{attr.key.name}}</td>
                    <td>{{attr.value.name}}</td>
                </tr>
                </tbody>
            </table>
        </div>
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