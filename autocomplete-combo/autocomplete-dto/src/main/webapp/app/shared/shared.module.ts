import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { AutocompletedtoSharedLibsModule, AutocompletedtoSharedCommonModule, AudLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [AutocompletedtoSharedLibsModule, AutocompletedtoSharedCommonModule],
    declarations: [AudLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [AudLoginModalComponent],
    exports: [AutocompletedtoSharedCommonModule, AudLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutocompletedtoSharedModule {}
