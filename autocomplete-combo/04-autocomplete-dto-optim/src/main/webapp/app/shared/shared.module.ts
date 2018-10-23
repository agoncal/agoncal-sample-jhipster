import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import {
    AutocompletedtooptimSharedLibsModule,
    AutocompletedtooptimSharedCommonModule,
    AudLoginModalComponent,
    HasAnyAuthorityDirective
} from './';

@NgModule({
    imports: [AutocompletedtooptimSharedLibsModule, AutocompletedtooptimSharedCommonModule],
    declarations: [AudLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [AudLoginModalComponent],
    exports: [AutocompletedtooptimSharedCommonModule, AudLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompletedtooptimSharedModule {}
