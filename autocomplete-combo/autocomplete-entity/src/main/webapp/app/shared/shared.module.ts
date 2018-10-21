import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import {
    AutocompleteentitySharedLibsModule,
    AutocompleteentitySharedCommonModule,
    AueLoginModalComponent,
    HasAnyAuthorityDirective
} from './';

@NgModule({
    imports: [AutocompleteentitySharedLibsModule, AutocompleteentitySharedCommonModule],
    declarations: [AueLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [AueLoginModalComponent],
    exports: [AutocompleteentitySharedCommonModule, AueLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompleteentitySharedModule {}
