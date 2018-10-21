import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AutocompletedtoContactModule } from './contact/contact.module';
import { AutocompletedtoLanguageModule } from './language/language.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AutocompletedtoContactModule,
        AutocompletedtoLanguageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompletedtoEntityModule {}
