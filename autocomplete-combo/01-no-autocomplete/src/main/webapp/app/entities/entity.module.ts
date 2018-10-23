import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NoautocompleteContactModule } from './contact/contact.module';
import { NoautocompleteLanguageModule } from './language/language.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        NoautocompleteContactModule,
        NoautocompleteLanguageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NoautocompleteEntityModule {}
