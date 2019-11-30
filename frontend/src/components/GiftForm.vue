<template>
    <div>
        <h1>{{hello}}</h1>
        <div>
            <label>
                {{ $t("message.family_name") }}
                <input type="text" v-model="new_family_name"
                       :placeholder="$t('message.family_name_placeholder')"
                       @keyup.enter="addFamily"/>
            </label>
            <ul v-if="errors">
                <li v-for="error in errors" :key="error">{{error}}</li>
            </ul>
            <input type="submit" :value="$t('message.send')" @click="addFamily"/>
        </div>
        <div>
            <FamilyForm v-for="family in families" v-bind:key="family.name" v-bind:family="family"/>
        </div>
    </div>
</template>

<script>

    import FamilyForm from "./FamilyForm";
    import {mapState} from 'vuex';


    export default {
        name: "GiftForm",
        data: function () {
            return {
                new_family_name: '',
                errors: [],
                hello: 'waiting'
            }
        },
        computed: mapState({
            families: state => state.families
        }),
        components: {
            FamilyForm
        },
        methods: {
            addFamily: function () {
                this.$data.errors = []
                const name = this.$data.new_family_name.trim()
                const already_exist = this.$store.state.families
                    .filter(x => x.name.toLowerCase() === name.toLowerCase()).length > 0
                const name_empty = name === ""
                if (name_empty) {
                    this.$data.errors.push("Family name cannot be empty.")
                }
                if (already_exist) {
                    this.$data.errors.push("This family name already exits.")
                }
                if (!name_empty && !already_exist) {
                    this.$store.dispatch('addFamily', {name: name})
                    this.$data.new_family_name = '';
                }
            }
        }
        /*,
        mounted() {
            axios.get('http://localhost:8081/hello').then(response => this.hello = response.data)

        }*/
    }
</script>

<style scoped>

</style>