import Vue from 'vue'
import Vuex from 'vuex'
import VueI18n from 'vue-i18n'
import App from './App.vue';
import axios from 'axios';

Vue.config.productionTip = false;

Vue.use(Vuex);
Vue.use(VueI18n);

const store = new Vuex.Store({
    strict: true,
    state: {
        families: [
            {name: "leduc", members: [{name: "manuel"}, {name: "flora"},]},
            {name: "plaisance", members: [{name: "justine"}, {name: "audrina"},]},
        ],
    },
    mutations: {
        new_family: function (state, name) {
            state.families.push({name: name, members: []})
        },
        new_family_member: function (state, params) {
            state.families = state.families.map(f => {
                if (f.name === params.family) {
                    f.members.push({name: params.name})
                }
                return f
            })
        },
        remove_member: function (state, params) {
            const family = params.family;
            const name = params.name;
            const f = state.families.filter(f => f.name === family)[0];
            f.members = f.members.filter(m => m.name !== name)
        },
        remove_family: function (state, params) {
            state.families = state.families.filter(f => f.name !== params.name)
        },
        set_result: function (state, params) {
            state.result = params.result
        }
    },
    actions: {
        addFamily: function (context, params) {
            context.commit('new_family', params.name);
        },
        addMember: function (context, params) {
            context.commit({
                type: 'new_family_member',
                family: params.family,
                name: params.name
            })
        },
        removeMember: function (context, params) {
            context.commit({
                type: 'remove_member',
                'family': params.family, 'name': params.name,
            })
        },
        removeFamily: function (context, params) {
            context.commit({
                type: 'remove_family',
                'name': params.name,
            })
        },
        setResult: function (context, result) {
            context.commit({type: 'set_result', result: result})
        }
    }
});

store.watch((state) => {
        return {families: state.families, members: state.families.map(x => x.members)}
    },
    (newVal) => {
        const params = newVal.families.map(f => ({
            name: f.name,
            personnes: f.members.map(p => ({
                name: p.name
            }))

        }));
        axios.post('http://localhost:8081/solution', params).then(re => {
            store.dispatch('setResult', re.data)
        })
    }
);

const params = store.state.families.map(f => ({
    name: f.name,
    personnes: f.members.map(p => ({
        name: p.name
    }))

}));

axios.post('http://localhost:8081/solution', params).then(re => {
    store.dispatch('setResult', re.data)
});

const messages = {
    fr: {
        message: {
            family: 'Famille',
            family_name: 'Nom de la famille',
            family_name_placeholder: 'Saisir le nom de la famille',
            send: 'Envoyer',
            new_member_placeholder: 'Saisir le nom du nouveau membre'
        }
    },
    en: {
        message: {
            family: 'Family',
            family_name: 'Family\'s last name',
            family_name_placeholder: 'Type the last name of the family',
            send: 'Send',
            new_member_placeholder: 'Type the first name of the family member'
        }
    }
};

const i18n = new VueI18n({
    locale: 'fr',
    messages
});

new Vue({
    el: '#app',
    store,
    i18n,
    render: h => h(App),
});//.$mount('#app')
