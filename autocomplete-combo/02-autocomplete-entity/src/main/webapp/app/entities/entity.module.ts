import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AutocompleteentityContactModule } from './contact/contact.module';
import { AutocompleteentityLanguageModule } from './language/language.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AutocompleteentityContactModule,
        AutocompleteentityLanguageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompleteentityEntityModule {}
