import Vue from 'vue';
import Vuex from 'vuex';

const LOGIN = "LOGIN";
const LOGIN_SUCCESS = "LOGIN_SUCCESS";
const LOGOUT = "LOGOUT";

Vue.use(Vuex);

export const store = new Vuex.Store({
    state:{
        isLoggedIn: localStorage.getItem("userId_token")
    },
    mutations: {
        [LOGIN](state) {
          state.pending = true;
        },
        [LOGIN_SUCCESS](state) {
          state.isLoggedIn = true;
          state.pending = false;
        },
        [LOGOUT](state) {
          state.isLoggedIn = false;
        }
    },
    actions: {
        login({
          state,
          commit,
          rootState
        }, creds) {        
          commit(LOGIN); // show spinner
          return new Promise(resolve => {
            setTimeout(() => {
              localStorage.setItem("userId_token", creds.token);
              commit(LOGIN_SUCCESS);
              resolve();
            }, 500);
          });
    
        },
        logout({ commit }) {
          localStorage.removeItem("userId_token");
          commit(LOGOUT);
        }
    },
    getters: {
        isLoggedIn: state => {
          return state.isLoggedIn;
        },
        getToken: () => {
          return localStorage.getItem("userId_token")
        }
    }
})