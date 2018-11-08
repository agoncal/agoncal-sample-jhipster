import { NgModule } from '@angular/core';

import { JacksonviewsSharedLibsModule, FindLanguageFromKeyPipe, JacAlertComponent, JacAlertErrorComponent } from './';

@NgModule({
    imports: [JacksonviewsSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JacAlertComponent, JacAlertErrorComponent],
    exports: [JacksonviewsSharedLibsModule, FindLanguageFromKeyPipe, JacAlertComponent, JacAlertErrorComponent]
})
export class JacksonviewsSharedCommonModule {}
