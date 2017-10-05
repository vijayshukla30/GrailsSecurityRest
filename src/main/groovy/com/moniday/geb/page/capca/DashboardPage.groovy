package com.moniday.geb.page.capca

import geb.Page

class DashboardPage extends Page {
    static at = {
        title.contains("Caisse Régionale Provence Côte d'Azur")
    }
    static content = {
        pageTitle { title }
        /*racineGDC { $("div#racineGDC") }
        bloc {
            if (racineGDC && !racineGDC.empty) {
                racineGDC.children("div.bloc-pap-texte")
            }
        }
        advisor {
            if (bloc && !bloc.empty)
                bloc.children("p")[0]
        }*/

        accountTable {}
    }
}