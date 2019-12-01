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
        families: [],
        result: []
    },
    mutations: {
        initialiseStore(state) {
            // Check if the ID exists
            if (localStorage.getItem('store')) {
                this.replaceState(
                    Object.assign(state, JSON.parse(localStorage.getItem('store')))
                );
            }
        },
        new_family(state, name) {
            state.families.push({name: name, members: []})
        },
        new_family_member(state, params) {
            state.families = state.families.map(f => {
                if (f.name === params.family) {
                    f.members.push({name: params.name})
                }
                return f
            })
        },
        remove_member(state, params) {
            const family = params.family;
            const name = params.name;
            const f = state.families.filter(f => f.name === family)[0];
            f.members = f.members.filter(m => m.name !== name)
        },
        remove_family(state, params) {
            state.families = state.families.filter(f => f.name !== params.name)
        },
        set_result(state, params) {
            state.result = params.result
        }
    },
    actions: {
        addFamily(context, params) {
            context.commit('new_family', params.name);
        },
        addMember(context, params) {
            context.commit({
                type: 'new_family_member',
                family: params.family,
                name: params.name
            })
        },
        removeMember(context, params) {
            context.commit({
                type: 'remove_member',
                'family': params.family, 'name': params.name,
            })
        },
        removeFamily(context, params) {
            context.commit({
                type: 'remove_family',
                'name': params.name,
            })
        },
        setResult(context, result) {
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
            people: f.members.map(p => ({
                name: p.name
            }))

        }));
        axios.post('http://localhost:8081/solution', params).then(re => {
            store.dispatch('setResult', re.data)
        })
    }
);

store.subscribe((mutation, state) => {
    // Store the state object as a JSON string
    localStorage.setItem('store', JSON.stringify(state));
});

const params = store.state.families.map(f => ({
    name: f.name,
    people: f.members.map(p => ({
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
    beforeCreate() {
        this.$store.commit('initialiseStore');
    },
    i18n,
    render: h => h(App),
});//.$mount('#app')
