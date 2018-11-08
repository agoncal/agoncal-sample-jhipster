import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { JacksonviewsSharedLibsModule, JacksonviewsSharedCommonModule, JacLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
    imports: [JacksonviewsSharedLibsModule, JacksonviewsSharedCommonModule],
    declarations: [JacLoginModalComponent, HasAnyAuthorityDirective],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JacLoginModalComponent],
    exports: [JacksonviewsSharedCommonModule, JacLoginModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JacksonviewsSharedModule {}
