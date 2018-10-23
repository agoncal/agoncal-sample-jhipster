import { Route } from '@angular/router';

import { AudConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
    path: 'aud-configuration',
    component: AudConfigurationComponent,
    data: {
        pageTitle: 'configuration.title'
    }
};
