import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AutocompletedtooptimSharedModule } from 'app/shared';
import {
    ContactComponent,
    ContactDetailComponent,
    ContactUpdateComponent,
    ContactDeletePopupComponent,
    ContactDeleteDialogComponent,
    contactRoute,
    contactPopupRoute
} from './';
import { AutoCompleteModule } from 'primeng/autocomplete';

const ENTITY_STATES = [...contactRoute, ...contactPopupRoute];

@NgModule({
    imports: [AutocompletedtooptimSharedModule, AutoCompleteModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContactComponent,
        ContactDetailComponent,
        ContactUpdateComponent,
        ContactDeleteDialogComponent,
        ContactDeletePopupComponent
    ],
    entryComponents: [ContactComponent, ContactUpdateComponent, ContactDeleteDialogComponent, ContactDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompletedtooptimContactModule {}
