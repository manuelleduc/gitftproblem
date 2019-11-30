<template>
    <div>
        <h1>{{$t('message.family')}} {{family.name}}</h1>
        <p>
            <label>
                New member:
                <input type="text" v-model="new_family_member"
                       :placeholder="$t('message.new_member_placeholder')"
                       @keyup.enter="addMember"/>
            </label>
        </p>
        <input type="submit" v-if="family.members.length===0" value="Delete" @click="delete_family"/>
        <div>
            <div v-for="member in family.members" :key="member.name">
                <h2>{{member.name}}</h2>
                <input type="button" @click="delete_member(member)" value="Delete"/>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "FamilyForm",
        props: ["family"],
        data: function () {
            return {
                new_family_member: "",
                errors: []
            }
        },
        methods: {
            delete_member: function (member) {
                this.$store.dispatch('removeMember', {
                    family: this.$props.family.name,
                    name: member.name,
                })
            },
            delete_family: function () {
                if (this.$props.family.members.length === 0) {
                    this.$store.dispatch('removeFamily', {
                        name: this.$props.family.name,
                    })
                }
            },
            addMember: function () {
                const name = this.$data.new_family_member.trim();
                const is_empty = name === '';
                if (!is_empty) {
                    this.$store.dispatch('addMember', {
                        family: this.$props.family.name,
                        name: name
                    })
                    this.$data.new_family_member = '';
                }
            }
        }
    }
</script>

<style scoped>

</style>